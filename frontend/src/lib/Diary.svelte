<script lang="ts">
  import { onMount } from "svelte";
  import NewPostForm from "./NewPostForm.svelte";
  import Post from "./Post.svelte";

  let date = new Date();
  let day = date.getDate();
  let year = date.getFullYear();
  let weekday = date.toLocaleString(navigator.language, { weekday: 'long' });
  let monthString = date.toLocaleString(navigator.language, { month: 'long' });

  let posts = [];

  onMount(async () => {
    const token = localStorage.getItem('token');
    const date = new Date();
    const day = date.getDate();
    const month = date.getMonth() + 1;

    try {
      const response = await fetch(`/api/posts/day?day=${day}`, {
        headers: {
          Authorization: `Bearer ${token}`
        }
      });

      if (response.ok) {
        const results = await response.json();
        posts = results.filter(post => post.month == month);
      } else {
        console.error('Error fetching posts.');
        posts = [{ id: 0, title: "Hello World!", content: "Have a nice day!", date: "2023-04-03"}];
        console.log(posts);
      }
    } catch (error) {
      console.error('Error fetching posts:', error);
    }
  });

  function deletePost(id: Number) {
    posts = posts.filter(post => post.id !== id);
  }
</script>

<div class="date">
  <div class="dateText">{weekday}</div>
  <div class="day">{day}</div>
  <div class="dateText">{monthString} {year}</div>
</div>

<NewPostForm />

<p>You posted this on {day} {monthString}:</p>

{#if posts.length > 0}
  {#each posts as post}
    <Post
      title={post.title}
      content={post.content}
      id={post.id}
      date={{"day": post.day, "month": post.month, "year": post.year}}
      deletePost={() => deletePost(post.id)}
    />
  {/each}
{:else}
None (Feel free to add something to your Diary)
{/if}

<style>
  .date {
    display: flex;
    flex-direction: column;
    align-items: center;
    font-size: 1.5rem;
    text-align: center;
    border: 2px solid white;
    padding: 1rem;
    border-radius: 10px;
    margin-bottom: 1rem;
  }

  .day {
    font-size: 4rem;
    font-weight: bold;
  }

  .dateText {
    margin-top: 0.5rem;
    font-size: 1.25rem;
  }
</style>