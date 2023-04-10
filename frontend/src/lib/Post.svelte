<script lang="ts">
  export let id: Number;
  export let title: String;
  export let content: String;
  export let date;
  export let deletePost; String;

  function getMonthName(monthNumber) {
    const date = new Date();
    date.setMonth(monthNumber - 1);

    return date.toLocaleString(undefined, { month: 'long' });
  }
  
  async function handleDelete() {
    const confirmDelete = confirm('Are you sure you want to delete this post?');
    if (confirmDelete) {
      const response = await fetch(`/api/posts/${id}`, {
        method: 'DELETE',
        headers: {
          Authorization: `Bearer ${localStorage.getItem('token')}`
        }
      });

      if (!response.ok) {
        console.error('Error deleting post.');
        return;
      }

      deletePost();
    }
  }
</script>

<div class="post">
  <div class="post-header">
    <h3>{title}</h3>
    <button on:click={handleDelete}>X</button>
  </div>
  <div class="post-content">
    <p>{content}</p>
  </div>
  <h4>{date.day} {getMonthName(date.month)} {date.year}</h4>
</div>

<style>
  .post {
    margin: 1rem 0;
    padding: 1rem;
    border: 1px solid #ddd;
    border-radius: 0.25rem;
  }

  .post-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 1rem;
  }

  .post-header h3 {
    margin: 0;
  }

  .post-header button {
    color: #ff0000;
    border: none;
    font-weight: bold;
    font-size: 1rem;
    cursor: pointer;
  }

  .post-header button:hover {
    background-color: #fff;
  }

  .post-content {
    font-size: 1.1rem;
    line-height: 1.5;
    text-align: left;
  }
</style>
