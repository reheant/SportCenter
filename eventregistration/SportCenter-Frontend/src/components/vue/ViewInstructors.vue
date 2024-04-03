<template>
    <div>
        <div>
        <b-navbar  class="navbar" toggleable="lg" type="dark" variant="info">
            <b-navbar-brand href="#">Sport Center</b-navbar-brand>

            <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

            <b-collapse id="nav-collapse" is-nav>
            <b-navbar-nav>
                <b-nav-item href="#" >Promote Customers</b-nav-item>
                <b-nav-item to="admin/createCourse" href="#">Create Course</b-nav-item>
            </b-navbar-nav>

            <b-navbar-nav class="ml-auto">
                <b-nav-item-dropdown right>
                <template #button-content>
                    <em>User</em>
                </template>
                <b-dropdown-item href="#">Sign Out</b-dropdown-item>
                </b-nav-item-dropdown>
            </b-navbar-nav>
            </b-collapse>
        </b-navbar>
        </div>
      
      <!-- Buttons for interaction -->
      <p>
        <b-button size="sm" class="button-custom" @click="selectAllRows">Select all</b-button>
        <b-button size="sm" class="button-custom" @click="clearSelected">Clear selected</b-button>
        <b-button size="sm" class="button-custom" @click="promoteCustomer">Demote Instructor</b-button>
        <b-button size="sm" class="button-custom" @click="deleteCustomer">Delete Instructor</b-button>
      </p>
  
      <!-- Table component -->
      <b-table
        :items="items"
        :fields="fields"
        :select-mode="selectMode"
        :current-page="currentPage"
        :per-page="perPage"
        responsive="sm"
        ref="selectableTable"
        :sort-by.sync="sortBy"
        :sort-desc.sync="sortDesc"
        sort-icon-right
        selectable
        @row-selected="onRowSelected"
      >
  
        <template #cell(selected)="{ rowSelected, item }">
          <span 
            @click="selectRow(item)"
            :class="{ 'selected-row': rowSelected }"
          >
            <template v-if="rowSelected">
              <span aria-hidden="true">&check;</span>
              <span class="sr-only">Selected</span>
            </template>
            <template v-else>
              <span aria-hidden="true">&nbsp;</span>
              <span class="sr-only">Not selected</span>
            </template>
          </span>
        </template>
        
      <template #cell(course_description)="row">
          <b-button size="sm" @click="row.toggleDetails" class="description-button">
         {{ row.detailsShowing ? 'Hide' : 'Show' }} Description
        </b-button>
      </template>
  
      <template #row-details="row">
        <b-card>
          <b-row class="mb-2">
            <b-col sm="3" class="text-sm-right"><b>Course Description:</b></b-col>
            <b-col>{{ row.item.course_description }}</b-col>
          </b-row>
        </b-card>
      </template>
  
          <!-- As `row.showDetails` is one-way, we call the toggleDetails function on @change -->
       
  
  
        <!-- Apply class for course status -->
        <template #cell(course_status)="data">
          <span 
            :class="{
              'text-rejected' : data.value === 'Refused',
              'text-pending' : data.value === 'Pending',
              'text-approved' : data.value === 'Approved',
            }"
          >
            {{ data.value !== null && data.value !== undefined ? data.value : ''}}
          </span>
        </template>
  
        <template #cell(course_name)="data">
          <span class="course-name">
            {{ data.value }}
          </span>
        </template>
  
      
    </b-table>
      
  
      <b-pagination 
        class ="pagination"
        v-model="currentPage"
        :total-rows="totalRows"
        :per-page="perPage"
        align="center"
        aria-controls="selectableTable"
        
      ></b-pagination>
      
    </div>
  </template>
    
    

<script src="../javascript/ViewInstructors.js" > </script>


<style>
    .navbar {
      position: fixed;
      top: 0;
      width: 100%;
      z-index: 1000; 
    }
    
    body {
      padding-top: 56px; 
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

  /* Style the table headers */
  .b-table th {
    background-color: #f0f0f0; 
    color: #333;
  }

  /* Style the table rows */
  .b-table tr {
    background-color: #ffffff; 
  }
  /* Style the selected rows */
  .b-table tr.selected {
    background-color: #f0f0ff; 
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

.course-name {
  font-weight: bold;
}



.description-button{
  background-color: #c3fcc5; 
  border: none;
  color: rgb(2, 2, 2);
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

.pagination {
  color: #b81b1b;
  font-weight: bold;
  font-size: 1.25rem;
  fill: blanchedalmond;
  color: white;
  margin: 10px;
  cursor: pointer;
}

.pagination a {
  border-radius: 5px;
}

.pagination a.active {
  border-radius: 5px;
}
</style>