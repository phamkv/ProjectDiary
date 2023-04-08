<script>
  import { navigate } from 'svelte-routing';
  import { loggedIn } from '../stores';

  function handleSignOut() {
    localStorage.removeItem('username');
    localStorage.removeItem('token');
    loggedIn.set(false);
    navigate('/');
  }

  handleSignOut()
</script>
