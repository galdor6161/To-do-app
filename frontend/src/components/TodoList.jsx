import React, { useState } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { updateTodo, deleteTodo, toggleCompleteTodo } from '../redux/actions';
import ReactPaginate from 'react-paginate';

// Component to list all Todo items
function TodoList() {
  const todos = useSelector((state) => state.todos);
  const dispatch = useDispatch();
  const [isEditing, setIsEditing] = useState(null);
  const [editTitle, setEditTitle] = useState('');
  const [editDescription, setEditDescription] = useState('');
  const [searchTerm, setSearchTerm] = useState('');
  const [currentPage, setCurrentPage] = useState(0);
  const todosPerPage = 5;

  const handleUpdate = (todo) => {
    setIsEditing(todo.id);
    setEditTitle(todo.title);
    setEditDescription(todo.description);
  };

  const handleSave = (id) => {
    dispatch(updateTodo({ id, title: editTitle, description: editDescription }));
    setIsEditing(null);
    setEditTitle('');
    setEditDescription('');
  };

  const handleComplete = (id) => {
    dispatch(toggleCompleteTodo(id));
  };

  const filteredTodos = todos.filter((todo) =>
    todo.title.toLowerCase().includes(searchTerm.toLowerCase())
  );

  const pageCount = Math.ceil(filteredTodos.length / todosPerPage);
  const handlePageClick = ({ selected }) => {
    setCurrentPage(selected);
  };

  const offset = currentPage * todosPerPage;
  const currentTodos = filteredTodos.slice(offset, offset + todosPerPage);

  return (
    <div>
      <input
        type="text"
        placeholder="Search todos..."
        value={searchTerm}
        onChange={(e) => setSearchTerm(e.target.value)}
        className="search-bar"
      />
      <ul className="todo-list">
        {currentTodos.map((todo) => (
          <li key={todo.id} className={`todo-item ${todo.completed ? 'completed' : ''}`}>
            <input
              type="checkbox"
              checked={todo.completed}
              onChange={() => handleComplete(todo.id)}
            />
            {isEditing === todo.id ? (
              <>
                <input
                  type="text"
                  value={editTitle}
                  onChange={(e) => setEditTitle(e.target.value)}
                />
                <input
                  type="text"
                  value={editDescription}
                  onChange={(e) => setEditDescription(e.target.value)}
                />
                <button onClick={() => handleSave(todo.id)}>Save</button>
                <button onClick={() => setIsEditing(null)}>Cancel</button>
              </>
            ) : (
              <>
                <div className="todo-details">
                  <span className="todo-title">{todo.title}</span>
                  <span className="todo-description">{todo.description}</span>
                </div>
                <div className="actions">
                  <button onClick={() => handleUpdate(todo)}>Update</button>
                  <button onClick={() => dispatch(deleteTodo(todo.id))}>Delete</button>
                </div>
              </>
            )}
          </li>
        ))}
      </ul>
      <ReactPaginate
        previousLabel={"previous"}
        nextLabel={"next"}
        breakLabel={"..."}
        pageCount={pageCount}
        marginPagesDisplayed={2}
        pageRangeDisplayed={3}
        onPageChange={handlePageClick}
        containerClassName={"pagination"}
        activeClassName={"active"}
      />
    </div>
  );
}

export default TodoList;