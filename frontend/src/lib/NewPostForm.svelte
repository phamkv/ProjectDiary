<script>
  import { navigate } from 'svelte-routing';
  import { postsStore } from '../stores';

  export let selectedDate;
  
  let title = '';
  let content = '';
  let errorMessage = '';
  let successMessage = '';
  
  function handleSubmit(event) {
    event.preventDefault();

    const day = selectedDate.getDate();
    const month = selectedDate.getMonth() + 1;
    const year = selectedDate.getFullYear();
    
    const token = localStorage.getItem('token');
    if (!token) {
      console.error('Error: no authentication token found.');
      navigate('/signin');
      return;
    }
    
    fetch('/api/posts', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
      },
      body: JSON.stringify({ title, content, day, month, year }),
    })
      .then(async response => {
        if (!response.ok) {
          console.error('Error creating new post.');
          errorMessage = 'Error creating new post.';
        } else {
          title = '';
          content = '';
          errorMessage = '';
          const post = await response.json();
          postsStore.update(value => [...value, post]);
          successMessage = 'Post created successfully!';
          setTimeout(() => {
            successMessage = '';
          }, 5000);
        }
      })
      .catch(error => {
        console.error('Error creating new post:', error);
        errorMessage = 'Error creating new post.';
      });
  }
</script>

<style>
  .new-post-form {
    display: flex;
    flex-direction: column;
    align-items: center;
    padding: 2rem;
    border: 1px solid #ddd;
    border-radius: 5px;
  }
  
  .new-post-form label {
    font-weight: bold;
    margin-bottom: 0.5rem;
  }
  
  .new-post-form input[type="text"],
  .new-post-form textarea {
    width: 100%;
    padding: 0.5rem;
    margin-bottom: 1rem;
    border-radius: 3px;
    border: 1px solid #ccc;
  }
  
  .new-post-form textarea {
    height: 10rem;
  }
  
  .new-post-form button {
    padding: 0.5rem 1rem;
    background-color: #4caf50;
    color: #fff;
    border: none;
    border-radius: 3px;
    cursor: pointer;
  }
  
  .new-post-form button:hover {
    background-color: #3e8e41;
  }
  
  .new-post-form p {
    color: red;
    font-weight: bold;
    margin-bottom: 0.5rem;
  }
</style>

<div class="new-post-form">
  <h2>Add a new post to your Diary</h2>
  
  {#if errorMessage}
    <p>{errorMessage}</p>
  {/if}

  {#if successMessage}
    <p style="color: green;">{successMessage}</p>
  {/if}
  
  <form on:submit={handleSubmit}>
    <label for="title">Title</label>
    <input type="text" id="title" bind:value={title} />
    
    <label for="content">Content</label>
    <textarea id="content" bind:value={content}></textarea>
    
    <button type="submit">Create Post</button>
  </form>
</div>
