<script lang="ts">
  import { onMount } from "svelte";
  import NewPostForm from "./NewPostForm.svelte";
  import Post from "./Post.svelte";
  import { postsStore } from '../stores';

  let selectedDate = new Date();
  let day = selectedDate.getDate();
  let year = selectedDate.getFullYear();
  let weekday = selectedDate.toLocaleString(navigator.language, { weekday: 'long' });
  let monthString = selectedDate.toLocaleString(navigator.language, { month: 'long' });

  let posts = [];

  const unsubscribe = postsStore.subscribe(value => {
    posts = value;
  });

  async function fetchPostsForSelectedDate() {
    const token = localStorage.getItem('token');
    const selectedDay = selectedDate.getDate();
    const selectedMonth = selectedDate.getMonth() + 1;

    try {
      const response = await fetch(`/api/posts/day?day=${selectedDay}`, {
        headers: {
          Authorization: `Bearer ${token}`
        }
      });

      if (response.ok) {
        const results = await response.json();
        postsStore.update(value => results.filter(post => post.month == selectedMonth));
      } else {
        console.error('Error fetching posts.');
      }
    } catch (error) {
      console.error('Error fetching posts:', error);
    }
  }

  onMount(() => {
    fetchPostsForSelectedDate();
  });

  function handleDateChange(event) {
    event.preventDefault();
    const dateRegex = /^\d{4}-\d{2}-\d{2}$/;
    if (!dateRegex.test(event.target[0].value)) {
      return
    }

    selectedDate = new Date(event.target[0].value);
    day = selectedDate.getDate();
    year = selectedDate.getFullYear();
    weekday = selectedDate.toLocaleString(navigator.language, { weekday: 'long' });
    monthString = selectedDate.toLocaleString(navigator.language, { month: 'long' });

    fetchPostsForSelectedDate();
  }
</script>

<div class="date">
  <div class="dateText">{weekday}</div>
  <div class="day">{day}</div>
  <div class="dateText">{monthString} {year}</div>
</div>

<form on:submit={handleDateChange}>
  <label for="date-picker">Select a date:</label>
  <input type="date"
    id="date-picker"
    name="date-picker" 
    value={selectedDate.toISOString().substr(0, 10)}
  />
  <button type="submit">Go</button>
</form>

<NewPostForm selectedDate={selectedDate}/>

<p>You posted this on {day} {monthString}:</p>

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

<style>
  #date-picker {
    border-radius: 8px;
    border: 1px solid transparent;
    padding: 0.6em 1.2em;
    font-size: 1em;
    font-weight: 500;
    font-family: inherit;
    background-color: #1a1a1a;
    transition: border-color 0.25s;
  }

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

  @media (prefers-color-scheme: light) {
    #date-picker {
      background-color: #f2f2f2;
    }
  }
</style>