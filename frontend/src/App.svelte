<script lang="ts">
  import { Router, Route, Link } from 'svelte-routing';
  import Signin from './routes/Signin.svelte';
  import Home from './routes/Home.svelte';
  import Signup from './routes/Signup.svelte';
  import Signout from './routes/Signout.svelte';
  import ProfileSettings from './routes/ProfileSettings.svelte';
  import { loggedIn } from './stores';

  let authenticated;

  loggedIn.subscribe(value => {
		authenticated = value;
	});
</script>

<main>
  <Router>
    <nav>
      <Link to="/" class="links">Home</Link>
      {#if authenticated}
        <Link to="/profile" class="links">Profile</Link>
        <Link to="/signout" class="links">Sign Out</Link>
      {:else}
        <Link to="/signin" class="links">Sign In</Link>
      {/if}
    </nav>
    <div>
      <Route path="/"><Home /></Route>
      <Route path="/signin"><Signin /></Route>
      <Route path="/signup"><Signup /></Route>
      <Route path="/signout"><Signout /></Route>
      <Route path="/profile"><ProfileSettings /></Route>
    </div>
  </Router>

  <p class="read-the-docs">
    Personal Hobby Project by <a href="https://www.github.com/phamkv">Kevin Pham</a>
  </p>
</main>

<style>
  nav :global(a) {
    margin-right: 1rem;
  }

  .read-the-docs {
    color: #888;
  }
  
</style>
