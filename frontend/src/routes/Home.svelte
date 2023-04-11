<script lang="ts">
  import diaryLogo from '../assets/diary.svg'
  import Counter from '../lib/Counter.svelte'
  import PingButton from '../lib/PingButton.svelte';
  import { onMount } from 'svelte';
  import { loggedIn } from '../stores';
  import Diary from '../lib/Diary.svelte';

  let authenticated;
  let username;

  loggedIn.subscribe(value => {
    authenticated = value;
  });

  onMount(async () => {
		if (authenticated) {
      username = localStorage.getItem("username");
    }
	});
</script>

<main>
  <div>
      <img src={diaryLogo} class="logo" alt="Diary Logo" />
  </div>

  {#if username}
    <p>Hello {username}! Today is:</p>
    <Diary />
  {:else}
    <p>Please log in to use the service</p>
  {/if}

  <div class="card">
    <Counter />
    <PingButton />
  </div>

  <p>
    Check out <a href="https://github.com/sveltejs/kit#readme" target="_blank" rel="noreferrer">SvelteKit</a>, the official Svelte app framework powered by Vite!
  </p>
</main>

<style>
  .logo {
    height: 6em;
    padding: 1.5em;
    will-change: filter;
    transition: filter 300ms;
    filter: invert(99%) sepia(4%) saturate(264%) hue-rotate(200deg) brightness(112%) contrast(95%);
  }
</style>
