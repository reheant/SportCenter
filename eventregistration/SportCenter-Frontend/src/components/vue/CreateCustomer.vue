<template>
    <div class="form-container">
      <b-card>
        <b-form @submit.prevent="onSubmit" @reset.prevent="onReset" v-if="show" class="form-content">
          <div class="title"> Create an Account </div>
  
          <b-form-group
            id="input-group-2"
            label="Your First Name:"
            label-for="input-first-name"
            class="form-group-content"
          >
            <b-form-input
              id="input-first-name"
              v-model="form.firstName"
              placeholder="Enter First Name"
              required
            ></b-form-input>
          </b-form-group>
  
          <b-form-group
            id="input-group-3"
            label="Your Last Name:"
            label-for="input-last-name"
            class="form-group-content"
          >
            <b-form-input
              id="input-last-name"
              v-model="form.lastName"
              placeholder="Enter Last Name"
              required
            ></b-form-input>
          </b-form-group>
  
          <b-form-group
            id="input-group-1"
            label="Email address:"
            label-for="input-email"
            class="form-group-content"
          >
            <b-form-input
              id="input-email"
              v-model="form.email"
              type="email"
              placeholder="Enter email"
              required
            ></b-form-input>
          </b-form-group>
  
          <b-form-group
            id="input-group-4"
            label="Password:"
            label-for="input-password"
            description="Password must have at least: one lower case letter, one upper case letter, one digit, one special character and be 8 characters minimum"
            class="form-group-content"
          >
            <b-form-input
              id="input-password"
              v-model="form.password"
              type="password"
              placeholder="Enter Password"
              required
            ></b-form-input>
          </b-form-group>
  
          <b-form-group
            id="input-group-5"
            label="Email Preferences:"
            v-slot="{ ariaDescribedby }"
            class="form-group-content"
          >
           
            <b-form-checkbox v-model="form.checked"> Wants Email Confirmations</b-form-checkbox>
          </b-form-group>
          <div class="error">
            <p v-if="error" style="color: red">{{ error }}</p>  
          </div>
          <div class="buttons-container">
            <b-button type="reset" variant="info">Reset</b-button>
            <b-button type="submit" variant="primary">Next</b-button>
          </div>
        </b-form>
        <!-- <b-card class="mt-3" header="Form Data Result"> 
          <pre class="m-0">{{ form }}</pre>
        </b-card> -->
      </b-card>
    </div>
  </template>
  
  <script src="../javascript/CreateCustomer.js" > </script>
  
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