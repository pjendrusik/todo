import React, {useState} from "react";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Form from "react-bootstrap/Form";

import {toDoService} from "services";
import {UserContext} from "../../contexts";
import { Formik, Form as FormikForm } from "formik";
import {FormikInput} from "../../components";
import * as Yup from "yup";

interface ToDoData {
  id: string;
  userId: string;
  content: string;
  createdAt: string;
}

const validationSchema = Yup.object({
  content: Yup.string().min(3, "At least 3 characters required").required("Required"),
});

type NewToDoParams = Yup.InferType<typeof validationSchema>;


export const ToDo2: React.FC = () => {
  const {
    dispatch,
    state: { apiKey, user },
  } = React.useContext(UserContext);

  const [todos, setToDos] = useState([]);

  const loadToDos = () => toDoService.getToDo(apiKey).then(response => {
    setToDos(response)
  })

  const handleSubmit = (values: NewToDoParams) => {
    toDoService.newToDo(apiKey, values).then(() => loadToDos())
  }
  
  React.useEffect(() => {
    loadToDos();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return (
    <Container>
      <Row>
        <h3>To do list:</h3>
        <div>
          {todos.map((item: ToDoData) => <li key={item.id}>{item.id} - {item.content}</li>)}
        </div>
      </Row>
      <Row>
        <h3 className="mb-4">New todo</h3>
        <Formik<NewToDoParams>
          initialValues={{
            content: "",
          }}
          onSubmit={ (values) => handleSubmit(values)}
          validationSchema={validationSchema}
        >
          <Form as={FormikForm}>
            <FormikInput name="content" label="ToDo" />
            <button type="submit">Submit</button>
          </Form>
        </Formik>
      </Row>
    </Container>
  )
}
