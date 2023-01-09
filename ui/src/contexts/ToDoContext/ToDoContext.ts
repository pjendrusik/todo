import React from "react";
import immer from "immer";

export interface NewToDo {
  content: string;
}

export interface ToDo {
  id: string,
  userId: string,
  content: string,
  createdAt: string
}

export interface ToDoState {
  todos: ToDo[] | null,
  newToDo: NewToDo | null
}

export const initialToDoState: ToDoState = {
  todos: null,
  newToDo: null
}

export type ToDoAction =
  | { type: "GET_TODOS"; todos: ToDo[] | null }
  | { type: "POST_TODO"; todo: NewToDo };

const todoReducer = (state: ToDoState, action: ToDoAction): ToDoState => {
  switch (action.type) {
    case "GET_TODOS":
      return immer(state, (tmp) => {
        tmp.todos = action.todos
      });
    case "POST_TODO":
      return immer(state, (tmp) => {
        tmp.newToDo = action.todo
      });
    default:
      return state
  }
}

export const ToDoContext = React.createContext<{
  state: ToDoState;
  dispatch: React.Dispatch<ToDoAction>;
}>({
  state: initialToDoState,
  dispatch: () => {},
});
