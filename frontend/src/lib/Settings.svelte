<script>
  import { navigate } from 'svelte-routing';

  let username = localStorage.getItem('username');
  
  function closeAccount() {
    if (confirm('Are you sure you want to close your account?')) {
      const token = localStorage.getItem('token');

      fetch('/auth/profile', {
        method: 'DELETE',
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
        .then(res => {
          if (res.ok) {
            navigate('/signout');
          } else {
            throw new Error('Failed to delete profile');
          }
        })
        .catch(err => {
          console.error(err);
          alert('Failed to delete profile');
        });
    }
  }
</script>

<div>
  <h1>Settings</h1>
  <p>Username: {username}</p>
  <button on:click={closeAccount}>Close Account</button>
</div>

<style>
</style>
