import React from 'react';
import TodoCreate from './components/TodoCreate';
import TodoList from './components/TodoList';
import './styles/App.css';

// Main application component with form and list of todos
function App() {
  return (
    <div className="app-wrapper">
      <div className="container">
        <header className="app-header">
          <h1 className="app-title">Todo Application</h1>
        </header>
        <div className="app-content">
          <TodoCreate />
          <TodoList />
        </div>
      </div>
    </div>
  );
}

export default App;