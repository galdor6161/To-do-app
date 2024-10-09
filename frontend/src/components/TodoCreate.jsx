import React, { useState } from 'react';
import { useDispatch } from 'react-redux';
import { addTodo } from '../redux/actions';

function TodoCreate() {
  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');
  const dispatch = useDispatch();

  const handleSubmit = (e) => {
    e.preventDefault();
    if (title.trim()) {
      dispatch(addTodo({ id: Date.now(), title: title.trim(), description, completed: false }));
      setTitle('');
      setDescription('');

      // Backend'e veri gönderimi (public endpointine)
      fetch("http://localhost:8080/public/", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ message: "Yeni görev eklendi: " + title }),
      })
        .then((response) => {
          if (!response.ok) {
            throw new Error("Bir hata oluştu, durum kodu: " + response.status);
          }
          return response.text();
        })
        .then((data) => {
          console.log("Backend'den yanıt: ", data);
        })
        .catch((error) => {
          console.error("Hata:", error);
        });
    }
  };

  return (
    <form onSubmit={handleSubmit} className="todo-create">
      <input
        type="text"
        placeholder="Title"
        value={title}
        onChange={(e) => setTitle(e.target.value)}
        required
      />
      <input
        type="text"
        placeholder="Description"
        value={description}
        onChange={(e) => setDescription(e.target.value)}
      />
      <button type="submit">Add Todo</button>
    </form>
  );
}

export default TodoCreate;

