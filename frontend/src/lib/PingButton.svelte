<script>
  export let label = 'Click me';
  export let endpoint = '/auth/ping';
  
  async function handleClick() {
    try {
      const response = await fetch(endpoint);
      const data = await response.text();
      console.log(data);
    } catch (error) {
      console.error(error);
    }
  }
</script>

<button on:click={handleClick}>{label}</button>