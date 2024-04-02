<template>
    <div>
      <div id="help" style="margin-bottom: 20px;">
        <b-button-toolbar aria-label="Toolbar with button groups and dropdown menu">
          <b-button-group class="mx-1">
            <b-button> Create New Course </b-button>
            <b-button> Approve Course </b-button>
            <b-button> Disapprove Course </b-button>
          </b-button-group>
  
          <b-dropdown class="mx-1" right text="Filters">
            <b-dropdown-item>Item 1</b-dropdown-item>
            <b-dropdown-item>Item 2</b-dropdown-item>
            <b-dropdown-item>Item 3</b-dropdown-item>
          </b-dropdown>
  
        </b-button-toolbar>
      </div>
      <div id="test">
        <b-table striped hover :items="courses" :fields="fields" :per-page="perPage" :filter="filter" :select-mode="'single'"></b-table>
     </div>
    </div>
  </template>
  
  
  <script>
  export default {
    data() {
      return {
        courses: [
          { 
            id: 1,
            course_name: 'Introduction to Programming',
            instructor: 'John Doe',
            duration: '10 weeks',
            courseStatus:'Pending'
          },
          { 
            id: 2,
            course_name: 'Web Development Fundamentals',
            instructor: 'Jane Smith',
            duration: '8 weeks',
            courseStatus:'Pending'
          },
          { 
            id: 3,
            course_name: 'Data Science Essentials',
            instructor: 'Mike Johnson',
            duration: '12 weeks',
            courseStatus:'Pending'
          }
        ],
        fields: [
          { key: 'id', label: 'ID' },
          { key: 'course_name', label: 'Course Name' },
          { key: 'instructor', label: 'Instructor' },
          { key: 'duration', label: 'Duration' },
          { key: 'courseStatus', label: 'Course Status' }
        ]
      };
    }
  };