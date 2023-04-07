<!-- Button.svelte -->
<script>
  import { navigate } from 'svelte-routing';

  export let label;
  export let goto;

  function handleClick() {
    navigate(`${goto}`);
  }
</script>

<button on:click={handleClick}>{label}</button>
