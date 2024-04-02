<template>
  <div class="form-container">
    <b-card>
      <b-form @submit.prevent="onSubmit" @reset.prevent="onReset" v-if="show" class="form-content">
        <div class="title"> Login Page </div>
        <template>
        <div id="eventregistration">
        <div>Tim put your login stuff, just include the create account link below so those who dont have an acc can sign up</div>
        <div class="create-account-link">
        Don't have an account? <router-link to="/createCustomer">Create one here</router-link>
      </div>
      </div>
    </template>
      

      </b-form>
      <!-- <b-card class="mt-3" header="Form Data Result"> 
        <pre class="m-0">{{ form }}</pre>
      </b-card> -->
    </b-card>
  </div>
</template>

<script src="../javascript/login.js" > </script>

<style>
.error {
  padding: 10px;

}
.form-container {
  max-width: 600px;
  margin: auto;
}

.form-content {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.form-group-content {
  width: 100%; 
  max-width: 400px; 
  margin-bottom: 1rem;
}

.buttons-container {
  display: flex;
  justify-content: space-around;
  width: 100%;
  max-width: 400px;
}

.b-card {
  box-shadow: 0 4px 8px rgba(0,0,0,.05);
}
.title {
  font-size: 24px; 
  font-weight: bold;
  color: #333; 
  margin-top: 15px;
  margin-bottom: 15px; 
}


</style>



  
