<template>
    <div class="form-container">
      <b-card>
        <b-form @submit.prevent="onSubmit" @reset.prevent="onReset" v-if="show" class="form-content">
          <div class="title"> Add PayPal </div>

          <b-form-group
            id="input-group-2"
            label="Account Name:"
            label-for="input-first-name"
            class="form-group-content"
          >
            <b-form-input
              id="input-first-name"
              v-model="form.accountName"
              placeholder="Enter Account Name"
              required
            ></b-form-input>
          </b-form-group>

          <b-form-group
            id="input-group-1"
            label="Customer Account Email:"
            label-for="input-email"
            class="form-group-content"
          >
            <b-form-input
              id="input-email"
              v-model="form.customerEmail"
              type="email"
              placeholder="Enter Customer Email"
              required
            ></b-form-input>
          </b-form-group>

          <b-form-group
            id="input-group-1"
            label="PayPal Email:"
            label-for="input-email"
            class="form-group-content"
          >
            <b-form-input
              id="input-email"
              v-model="form.paypalEmail"
              type="email"
              placeholder="Enter PayPal Email"
              required
            ></b-form-input>
          </b-form-group>

          <b-form-group
            id="input-group-4"
            label="PayPal Password:"
            label-for="input-password"
            class="form-group-content"
          >
            <b-form-input
              id="input-password"
              v-model="form.password"
              type="password"
              placeholder="Enter PayPal Password"
              required
            ></b-form-input>
          </b-form-group>
          <div class="error">
            <p v-if="error" style="color: red">{{ error }}</p>
          </div>
          <div class="buttons-container">
            <b-button @click="onReturn" variant="info">Return</b-button>
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

  <script src="../javascript/AddPaypal.js" > </script>

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
