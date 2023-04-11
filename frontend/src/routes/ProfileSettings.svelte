<script>
  import AllPosts from '../lib/AllPosts.svelte';
  import Settings from '../lib/Settings.svelte';
  
  let selected = 'Settings';
  
  const changeSelected = (newSelected) => {
    selected = newSelected;
  }
</script>

<style>
  .sidebar {
    width: 200px;
    height: 100%;
    position: fixed;
    top: 0;
    left: 0;
  }

  .main {
    padding: 20px;
  }

  .selected {
    background-color: #2F2F2F;
  }

  ul {
    margin-top: 10rem;
    list-style: none;
  }
</style>

<div class="sidebar">
  <ul>
    <li class="{selected === 'Settings' ? 'selected' : ''}">
      <a href="/profile/settings" on:click|preventDefault={() => changeSelected('Settings')}>Settings</a>
    </li>
    <li class="{selected === 'AllPosts' ? 'selected' : ''}">
      <a href="/profile/allposts" on:click|preventDefault={() => changeSelected('AllPosts')}>All Posts</a>
    </li>
  </ul>
</div>

<div class="main">
  {#if selected == 'Settings'}
    <Settings />
  {:else}
    <AllPosts />
  {/if}
</div>