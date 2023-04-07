<script lang="ts">
  import { Router, Route, Link } from 'svelte-routing';
  import Signin from './routes/Signin.svelte';
  import Home from './routes/Home.svelte';
</script>

<main>
  <Router>
    <nav>
     <Link to="/">Home</Link>
     <Link to="/signin">Signin</Link>
    </nav>
    <div>
     <Route path="/"><Home /></Route>
     <Route path="/signin"><Signin /></Route>
    </div>
  </Router>

  <p class="read-the-docs">
    Personal Hobby Project by <a href="https://www.github.com/phamkv">Kevin Pham</a>
  </p>
</main>

<style>
  .read-the-docs {
    color: #888;
  }
</style>
