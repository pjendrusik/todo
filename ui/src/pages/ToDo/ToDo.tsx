import React, {useState} from "react";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Form from "react-bootstrap/Form";

import {toDoService, userService, versionService} from "services";
import {UserContext, ToDoContext} from "../../contexts";
import {usePromise} from "react-use-promise-matcher";
import Spinner from "react-bootstrap/Spinner";
import { Formik, Form as FormikForm } from "formik";
import {FeedbackButton, FormikInput} from "../../components";
import {BiArrowFromBottom} from "react-icons/bi";
import * as Yup from "yup";
import Moment from 'react-moment';


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


export const ToDo: React.FC = () => {
  const {
    dispatch,
    state: { apiKey, user },
  } = React.useContext(UserContext);

  const [todosResponse, load] = usePromise<ToDoData[], any, any>(() => toDoService.getToDo(apiKey));

  const [result, send, clear] = usePromise((newToDo: NewToDoParams) =>
    toDoService.newToDo(apiKey, newToDo).then(() => load())
  );

  React.useEffect(() => {
    load();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return (
    <Container>
      <Row>
        <h3>To do list:</h3>
        <div>
          {todosResponse.match({
            Idle: () => <></>,
            Loading: () => <Spinner animation="border" size="sm" role="loader" />,
            Rejected: () => <></>,
            Resolved:  (list) => <ul>{list.map((item) => <li key={item.id}>{item.content} - <Moment format="YYYY/MM/DD">{item.createdAt}</Moment></li>)}</ul>
          })}
        </div>
      </Row>
      <Row>
        <h3 className="mb-4">New todo</h3>
        <Formik<NewToDoParams>
          initialValues={{
            content: "",
          }}
          onSubmit={send}
          validationSchema={validationSchema}
        >
          <Form as={FormikForm}>
            <FormikInput name="content" label="ToDo" />

            <FeedbackButton
              className="float-end"
              type="submit"
              label="Add todo"
              variant="dark"
              Icon={BiArrowFromBottom}
              result={result}
              clear={clear}
              successLabel="Added new todo"
            />
          </Form>
        </Formik>
      </Row>
    </Container>
  )
}
