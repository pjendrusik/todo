import React from "react";
import { Link } from "react-router-dom";
//import Image from "react-bootstrap/Image";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Fade from "react-bootstrap/Fade";
//import logo from "assets/sml-logo-vertical-white-all-trans.png";

export const Welcome: React.FC = () => (
  <>
    <Container fluid className="py-5 bg-light text-dark">
      <Row className="h-100">
        <Fade appear in>
          <Container className="d-flex flex-column justify-content-center align-items-center">
            <h3>Hi there!</h3>
            <p className="mt-3 px-4">
              In this template application you can{" "}
              <Link to="/register" className="link-dark">
                Register
              </Link>{" "}
              as a new user,{" "}
              <Link to="/login" className="link-dark">
                Login
              </Link>{" "}
              and later manage your user details.
            </p>
          </Container>
        </Fade>
      </Row>
    </Container>
  </>
);
