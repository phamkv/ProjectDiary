<script lang="ts">
  import diaryLogo from '../assets/diary.svg'
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
  <h2>ProjectDiary</h2>

  <div>
      <img src={diaryLogo} class="logo" alt="Diary Logo" />
  </div>

  {#if username}
    <p>Hello {username}! Today is:</p>
    <Diary />
  {:else}
    <p>Please sign in to use the service</p>
  {/if}

  <p>
    This is a demo project. Please do not store sensible information. Check out the <a href="https://github.com/phamkv/projectdiary" target="_blank" rel="noreferrer">GitHub</a> repository.
  </p>
</main>

<style>
  @media (prefers-color-scheme: dark) {
    .logo {
      height: 6em;
      padding: 1.5em;
      filter: invert(99%) sepia(4%) saturate(264%) hue-rotate(200deg) brightness(112%) contrast(95%);
    }
  }
  @media (prefers-color-scheme: light) {
    .logo {
      height: 6em;
      padding: 1.5em;
    }
  }
</style>
