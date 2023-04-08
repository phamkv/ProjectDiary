<script>
  import NavigateButton from "../lib/NavigateButton.svelte";
  import { navigate } from 'svelte-routing';
  import { loggedIn } from '../stores';
  import { onMount } from "svelte";

  let username = "";
  let password = "";
  let errorMessage= "";

  let successMessage = "";

  onMount(() => {
    successMessage = localStorage.getItem('signupSuccessMessage');
    localStorage.removeItem('signupSuccessMessage');
  });

  async function handleSubmit(event) {
    event.preventDefault();

    const response = await fetch('/auth/signin', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ username, password })
    });

    if (!response.ok) {
      console.error('Error signing in.');
      errorMessage = 'Incorrect username or password';
      setTimeout(() => {
        errorMessage = '';
      }, 5000);
    }

    const object = await response.json();
    localStorage.setItem('token', object.token);
    localStorage.setItem('username', object.username);
    loggedIn.set(true);
    navigate('/');
  }
</script>

<h1>Sign In</h1>

{#if successMessage}
  <p style="color: green;">{successMessage}</p>
{/if}

{#if errorMessage}
  <p style="color: red;">{errorMessage}</p>
{/if}

<form on:submit={handleSubmit}>
  <div>
    <label for="username">Username:</label>
    <input type="text" id="username" bind:value={username} />
  </div>
  <div>
    <label for="password">Password:</label>
    <input type="password" id="password" bind:value={password} />
  </div>
  <button type="submit">Sign In</button>
</form>

<p>New Here? <NavigateButton label="Sign Up" goto="/signup"/></p>