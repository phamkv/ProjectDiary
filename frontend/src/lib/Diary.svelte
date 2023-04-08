<script>
  let date = new Date();
  let day = date.getDate();
  let year = date.getFullYear();
  let weekday = date.toLocaleString(navigator.language, { weekday: 'long' });
  let monthString = date.toLocaleString(navigator.language, { month: 'long' });

  let posts;
</script>

<div class="date">
  <div class="dateText">{weekday}</div>
  <div class="day">{day}</div>
  <div class="dateText">{monthString} {year}</div>
</div>

<p>You posted this on {day} {monthString}:</p>

{#if posts}
{posts}
{:else}
None (Feel free to add something to your Diary)
{/if}

<style>
  .date {
    display: flex;
    flex-direction: column;
    align-items: center;
    font-size: 1.5rem;
    text-align: center;
    border: 2px solid white;
    padding: 1rem;
    border-radius: 10px;
  }

  .day {
    font-size: 4rem;
    font-weight: bold;
  }

  .dateText {
    margin-top: 0.5rem;
    font-size: 1.25rem;
  }
</style>