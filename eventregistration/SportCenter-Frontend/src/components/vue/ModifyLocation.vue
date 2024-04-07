<template>
  <div class="form-container">
    <b-card>
      <b-form @submit.prevent="onSubmit" @reset.prevent="onReset" v-if="show" class="form-content">
        <div class="title">Modify Location</div>

        <b-form-group id="input-group-2" label="Name" label-for="input-name" class="form-group-content">
          <b-form-input id="input-name" v-model="form.name" placeholder="Name" required></b-form-input>
        </b-form-group>

        <b-form-group id="input-group-3" label="Capacity" label-for="input-capacity" class="form-group-content">
          <b-form-input id="input-capacity" type="number" v-model="form.capacity" placeholder="Capacity"
            required></b-form-input>
        </b-form-group>

        <b-form-group id="input-group-5" label="Opening Time:" label-for="input-opening-time"
          class="form-group-content">
          <b-form-timepicker id="input-email" v-model="form.openingTime" placeholder="Time"
            required></b-form-timepicker>
        </b-form-group>

        <b-form-group id="input-group-4" label="Closing Time:" label-for="input-closing-time"
          class="form-group-content">
          <b-form-timepicker id="input-closingTime" v-model="form.closingTime" placeholder="Time"
            required></b-form-timepicker>
        </b-form-group>
        <div class="error">
          <p v-if="error" style="color: red">{{ error }}</p>
        </div>
        <div class="buttons-container">
          <b-button type="reset" variant="info">Reset</b-button>
          <b-button type="submit" variant="primary">Save</b-button>
        </div>
        <p v-if="error" style="color: red">{{ error }}</p>
        <p v-if="successMessage" style="color: green">{{ successMessage }}</p>
      </b-form>
      <!-- <b-card class="mt-3" header="Form Data Result">
          <pre class="m-0">{{ form }}</pre>
        </b-card> -->
    </b-card>
  </div>
</template>

<script src="../javascript/ModifyLocation.js"></script>

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