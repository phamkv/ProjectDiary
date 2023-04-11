<script lang="ts">
  import { onMount } from "svelte";
  import Post from "./Post.svelte";
  import { postsStore } from '../stores';

  let posts = [];

  const unsubscribe = postsStore.subscribe(value => {
    posts = value;
  });

  onMount(async () => {
    const token = localStorage.getItem('token');

    try {
      const response = await fetch(`/api/posts/profile`, {
        headers: {
          Authorization: `Bearer ${token}`
        }
      });

      if (response.ok) {
        const results = await response.json();
        postsStore.update(value => results);
      } else {
        console.error('Error fetching posts.');
      }
    } catch (error) {
      console.error('Error fetching posts:', error);
    }
  });
</script>

{#if posts.length > 0}
  {#each posts as post}
    <Post
      title={post.title}
      content={post.content}
      id={post.id}
      date={{"day": post.day, "month": post.month, "year": post.year}}
    />
  {/each}
{:else}
None (Feel free to add something to your Diary)
{/if}