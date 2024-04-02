<template>
    <div>
      <!-- Table component -->
      <b-table
        :items="items"
        :fields="fields"
        :select-mode="selectMode"
        responsive="sm"
        ref="selectableTable"
        selectable
      >
        <!-- Scoped slot for select state -->
        <template #cell(selected)="{ rowSelected, items }">
                <!-- Add click event listener to toggle row selection -->
                <span @click="toggleRowSelection(items)">
                    <template v-if="rowSelected">
                        <!-- Selected icon -->
                        <span aria-hidden="true">&check;</span>
                        <span class="sr-only">Selected</span>
                    </template>
                    <template v-else>
                        <!-- Unselected icon -->
                        <span aria-hidden="true">&nbsp;</span>
                        <span class="sr-only">Not selected</span>
                    </template>
                </span>
        </template>


         <!-- Apply class for course status -->
         <template #cell(course_status)="data">
          <span 
            :class="{
              'text-rejected' : data.value === 'Rejected',
              'text-pending' : data.value === 'Pending',
              'text-approved' : data.value === 'Approved',
            }"
          >
          {{ data.value !== null && data.value !== undefined ? data.value : ''}}
          </span>
        </template>

      </b-table>
  
      <!-- Buttons for interaction -->
      <p>
        <b-button size="sm" class="button-custom" @click="selectAllRows">Select all</b-button>
        <b-button size="sm" class="button-custom" @click="clearSelected">Clear selected</b-button>

        <b-button size="sm" class="button-custom" @click="approveCourse"> Approve Course</b-button>
        <b-button size="sm" class="button-custom" @click="disapproveCourse"> Disapprove Course </b-button>

        <router-link to="/createCourse">
            <b-button size="sm" class="button-custom" >Create Course</b-button>
        </router-link>

      </p>
    
    </div>
</template>

<script src="../javascript/DisplayCourse.js" > </script>

<style>
  
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
  
  .title {
    font-size: 24px; 
    font-weight: bold;
    color: #333; 
    margin-top: 15px;
    margin-bottom: 15px; 
  }
  .b-table {
    border: 2px solid #ccc; /* Example: add a border */
    border-radius: 5px; /* Example: add border radius */
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /* Example: add a box shadow */
  }

  .b-table th {
    background-color: #f0f0f0; /* Example: Style the table headers */
    color: #333;
  }

  .b-table tr {
    background-color: #ffffff; /* Example: Style the table rows */
  }

  .b-table tr.selected {
    background-color: #f0f0ff; /* Example: Style the selected rows */
  }

  .b-table tr:hover {
    background-color: #f9f9f9; /* Example: Style the hover effect */
  }

  .text-rejected {
    color: red;
    font-weight: bold;
  }

  .text-pending {
    color: orange;
    font-weight: bold;
  }

  .text-approved {
    color: green;
    font-weight: bold;
  }


  .button-custom {
  background-color: #4CAF50; /* Green */
  border: none;
  color: white;
  padding: 8px 14px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 14px;
  margin: 4px 2px;
  transition-duration: 0.4s;
  cursor: pointer;
  border-radius: 4px;
}

.button-custom:hover {
  background-color: #45a049; /* Darker Green */
}
  </style>