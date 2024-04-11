<template>
  <div>
    <!-- Buttons for interaction -->
    <div>
      <b-navbar class="navbar" toggleable="lg" type="dark" variant="info">
        <b-navbar-brand href="#">Sport Center</b-navbar-brand>

        <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

        <b-collapse id="nav-collapse" is-nav>
          <b-navbar-nav>
            <b-nav-item to="/admin/viewCustomers">View Customers</b-nav-item>
            <b-nav-item to="/admin/viewInstructors" href="#"
              >View Instructors</b-nav-item
            >
            <b-nav-item to="/admin/viewOwners">View Owners</b-nav-item>
            <b-nav-item to="/admin/displayCourse" href="#"
              >View Courses</b-nav-item
            >
            <b-nav-item to="/admin/displaySessions" href="#"
              >View Sessions</b-nav-item
            >
            <b-nav-item to="/admin/modify/location" href="#"
              >Modify Location</b-nav-item
            >
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
      <router-link to="/admin/CreateSession">
        <b-button size="sm" class="button-custom">Create Session</b-button>
      </router-link>
      <b-button size="sm" class="button-custom" @click="deleteSession"
        >Delete Session</b-button
      >
      <router-link to="/admin/FilterSessions">
        <b-button size="sm" class="button-custom">Filter</b-button>
      </router-link>
      <b-button size="sm" class="button-custom" @click="displayAssignInstructor"
        >Assign an Instructor</b-button
      >
    </p>

    <div v-if="assigningInstructor">
      <div class="form-container">
        <b-card>
          <b-form
            @submit.prevent="onSubmit"
            @reset.prevent="onReset"
            class="form-content"
          >
            <b-form-group
              id="input-group-2"
              label="Instructor Email:"
              label-for="input-instructor-email"
              class="form-group-content"
            >
              <b-form-input
                id="input-instructor-email"
                v-model="form.instructorEmail"
                placeholder="Enter Instructor Email"
                required
              ></b-form-input>
            </b-form-group>

            <div class="buttons-container">
              <b-button type="reset" variant="info">Reset</b-button>
              <b-button
                type="submit"
                class="btn btn-danger"
                @click="unassignInstructor"
                >Unassign</b-button
              >
              <b-button
                type="submit"
                variant="primary"
                @click="assignInstructor"
                >Assign</b-button
              >
            </div>
          </b-form>
        </b-card>
      </div>
    </div>

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

        <template #cell(session_id)="data">
          <span class="session-id">
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

    <div class="error">
      <p v-if="error" style="color: red">{{ error }}</p>
    </div>
    <p v-if="successMessage" style="color: green">{{ successMessage }}</p>
  </div>
</template>

<script src="../javascript/DisplaySessionsAdmin.js"></script>

<style>
.form-group-content {
  width: 100%;
  max-width: 400px;
  margin-bottom: 1rem;
}

.form-content {
  display: flex;
  flex-direction: column;
  align-items: center;
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
  text-align: center;
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

.session-name {
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
