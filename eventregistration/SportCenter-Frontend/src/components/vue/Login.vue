<template>
  <div class="form-container">
    <b-card>
      <b-form @submit.prevent="onSubmit" @reset.prevent="onReset" v-if="show" class="form-content">
        <div class="title"> Login Page </div>

        <b-form-group id="input-group-1" label-for="input-email" class="form-group-content">
          <b-form-select v-model="form.userType">
            <b-form-select-option value="Customer">
              Customer
            </b-form-select-option>
            <b-form-select-option value="Instructor">
              Instructor
            </b-form-select-option>
            <b-form-select-option value="Owner">
              Owner
            </b-form-select-option>
          </b-form-select>
        </b-form-group>

        <b-form-group id="input-group-2" label="Email address" label-for="input-email" class="form-group-content">
          <b-form-input id="input-email" v-model="form.email" placeholder="Email Address" required></b-form-input>
        </b-form-group>

        <b-form-group id="input-group-3" label="Password:" label-for="input-password" class="form-group-content">
          <b-form-input id="input-password" v-model="form.password" type="password" placeholder="Enter Password"
            required></b-form-input>
        </b-form-group>

        <div class="error">
          <p v-if="error" style="color: red">{{ error }}</p>
        </div>
        <div class="buttons-container">
          <b-button type="submit" variant="primary">Login</b-button>
        </div>

        <div class="create-account-link">
          Don't have an account? <router-link to="/createCustomer">Create one here</router-link>
        </div>
      </b-form>
    </b-card>
  </div>
</template>

<script src="../javascript/login.js"></script>

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
  box-shadow: 0 4px 8px rgba(0, 0, 0, .05);
}

.title {
  font-size: 24px;
  font-weight: bold;
  color: #333;
  margin-top: 15px;
  margin-bottom: 15px;
}
</style>
