import axios, { AxiosRequestConfig } from "axios";
import * as Yup from "yup";

const context = "api/v1/todo";

const todoSchema = Yup.object().required().shape({
  id: Yup.string().required(),
  userId: Yup.string().required(),
  content: Yup.string().required(),
  createdAt: Yup.string().required(),
});

const newToDo = (apiKey: string | null, params: { content: string; }) =>
  _securedRequest(apiKey, {
    method: "POST",
    url: context,
    data: params,
  }).then(({ data }) => todoSchema.validate(data));

const getToDo = (apiKey: string | null) =>
  _securedRequest(apiKey, {
    method: "GET",
    url: context,
  }).then(({ data }) => data);

const _securedRequest = (apiKey: string | null, config: AxiosRequestConfig) =>
  axios.request({
    headers: {
      Authorization: `Bearer ${apiKey}`,
    },
    ...config,
  });

export const toDoService = {
  getToDo,
  newToDo
};
