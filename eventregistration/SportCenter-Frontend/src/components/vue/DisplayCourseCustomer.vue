<template>
  <div>
    <!-- Buttons for interaction -->
        <div>
          <b-navbar class="navbar" toggleable="lg" type="dark" variant="info">
            <b-navbar-brand href="#">Sport Center</b-navbar-brand>

            <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

            <b-collapse id="nav-collapse" is-nav>
              <b-navbar-nav>
                    <b-nav-item to="/customer/displayCourse" href="#">View Courses</b-nav-item>
                    <b-nav-item to="/customer/displaySessions" href="#">View Sessions</b-nav-item>
                </b-navbar-nav>

              <b-navbar-nav class="ml-auto">
                <b-nav-item-dropdown right>
                  <template #button-content>
                    <em>User</em>
                  </template>
                  <b-dropdown-item @click="onLogout">Sign Out</b-dropdown-item>
                </b-nav-item-dropdown>
              </b-navbar-nav>
            </b-collapse>
          </b-navbar>
        </div>
    <!-- Buttons for interaction -->
    <p>
      <b-button size="sm" class="button-custom" @click="selectAllRows"
        >Select All</b-button
      >
      <b-button size="sm" class="button-custom" @click="clearSelected"
        >Clear selected</b-button
      >
      <router-link to="/customer/FilterCourses">
        <b-button size="sm" class="button-custom">Filter</b-button>
      </router-link>
    </p>
    <div class="tableContainer">
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
        fixed="fixed"
        sort-icon-right
        class="custom-striped-table"
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
          <b-button
            size="sm"
            @click="row.toggleDetails"
            class="description-button"
          >
            {{ row.detailsShowing ? "Hide" : "Show" }} Description
          </b-button>
        </template>

        <template #row-details="row">
          <b-card>
            <b-row class="mb-2">
              <b-col sm="3" class="text-sm-right"
                ><b>Course Description:</b></b-col
              >
              <b-col>{{ row.item.course_description }}</b-col>
            </b-row>
          </b-card>
        </template>

        <!-- As `row.showDetails` is one-way, we call the toggleDetails function on @change -->

        <template #cell(course_name)="data">
          <span class="course-name">
            {{ data.value }}
          </span>
        </template>
      </b-table>
    </div>
    <b-pagination
      class="pagination"
      v-model="currentPage"
      :total-rows="totalRows"
      :per-page="perPage"
      align="center"
      aria-controls="selectableTable"
    ></b-pagination>
  </div>
</template>

<script src="../javascript/DisplayCourseCustomer.js"></script>
<style>
.tableContainer {
  max-width: 90%;
  margin: 0 auto;
}
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
  
  border: 2px solid #ccc; 
  border-radius: 5px; 
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); 
}

/* Style the table headers */
.b-table th {
  background-color: #f0f0f0;
  color: #333;
  white-space: nowrap;
}

/* Style the table rows */
.b-table tr {
  background-color: #ffffff;
  text-align: center;
}
/* Style the selected rows */
.b-table tr.selected {
  background-color: #f0f0ff;
  text-align: center;
}

.b-table tr:hover {
  background-color: #f9f9f9;
}

.b-table tr:hover {
  background-color: #f9f9f9;
}
.custom-striped-table tbody tr:nth-child(odd):not(:hover) {
  background-color: #00cffd19;
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
  background-color: #4caf50;
  border: none;
  color: white;
  padding: 8px 14px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-weight: bold;
  font-size: 1.25rem;
  margin: 2px;
  transition-duration: 0.4s;
  cursor: pointer;
  border-radius: 4px;
}

.button-custom:hover {
  background-color: #45a049;
}

.course-name {
  font-weight: bold;
}
.description-button {
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
.pagination .page-link {
  color: #4caf50;
  font-weight: bold;
  font-size: 1.25rem;
  margin: 10px;
  cursor: pointer;
}

.pagination .page-item.active .page-link {
  background-color: #4caf50;
  border-color: #4caf50;
}

.pagination .page-link:hover {
  background-color: #4caf50;
  border-color: #4caf50;
  color: white;
}

.pagination .page-link:focus {
  box-shadow: none;
}

.pagination .page-link,
.pagination .page-item.active .page-link {
  border-radius: 5px;
}
</style>
