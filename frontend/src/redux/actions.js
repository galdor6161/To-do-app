// Define action types
export const ADD_TODO = 'ADD_TODO';
export const DELETE_TODO = 'DELETE_TODO';
export const UPDATE_TODO = 'UPDATE_TODO';
export const TOGGLE_COMPLETE_TODO = 'TOGGLE_COMPLETE_TODO';

// Action creator to add a new todo
export const addTodo = (todo) => {
  return {
    type: ADD_TODO,
    payload: todo,
  };
};

// Action creator to delete a todo
export const deleteTodo = (id) => {
  return {
    type: DELETE_TODO,
    payload: id,
  };
};

// Action creator to update a todo
export const updateTodo = (todo) => {
  return {
    type: UPDATE_TODO,
    payload: todo,
  };
};

// Action creator to toggle the completion status of a todo
export const toggleCompleteTodo = (id) => {
  return {
    type: TOGGLE_COMPLETE_TODO,
    payload: id,
  };
};