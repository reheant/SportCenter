<template>
    <div class="form-container">
      <b-card class = "container">
          <div class="title"> Select Payment Method </div>
          <b-form-group label="Select Form:">
            <b-form-radio-group v-model="selectedForm" name="formOptions">
                <b-form-radio value="form1">PayPal</b-form-radio>
                <b-form-radio value="form2">Credit Card</b-form-radio>
            </b-form-radio-group>
            </b-form-group>


          <div class="buttons-container">
            <b-button @click="onSubmit" type="submit" variant="primary">Next</b-button>
          </div>
          <p v-if="error" style="color: red">{{ error }}</p>   
      </b-card>
    </div>
  </template>
  
  <script src="../javascript/SelectPaymentType.js" > </script>
  
  <style>
  .container {
    align-items: center;
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
    align-items: center;
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