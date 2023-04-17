<script>
  import { navigate } from 'svelte-routing';

  let username = "";
  let password = "";
  let errorMessage= "";

  async function handleSubmit(event) {
    event.preventDefault();

    const response = await fetch('/auth/signup', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ username, password })
    });

    if (response.ok) {
      localStorage.setItem('signupSuccessMessage', 'Your account has been created. You can now sign in.');
      navigate('/signin');
    } else {
      console.error('Error signing in.');
      errorMessage = 'There was an error while processing your sign up';
      setTimeout(() => {
        errorMessage = '';
      }, 5000);
    }
  }
</script>

<h1>Sign Up</h1>

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
  <button type="submit">Sign Up</button>
</form>

<style>
  div {
    margin-bottom: 0.5rem;
  }
</style>