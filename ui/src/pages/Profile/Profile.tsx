import React from "react";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import { ProfileDetails } from "./components/ProfileDetails";
import { PasswordDetails } from "./components/PasswordDetails";
import { ApiKeyDetails } from "./components/ApiKeyDetails";

export const Profile: React.FC = () => (
  <Container>
    <Row>
      <ProfileDetails />
      <PasswordDetails />
      <ApiKeyDetails />
    </Row>
  </Container>
);
