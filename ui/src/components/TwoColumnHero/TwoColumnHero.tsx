import Container from "react-bootstrap/Container";
import Col from "react-bootstrap/Col";
import Row from "react-bootstrap/Row";
import Fade from "react-bootstrap/Fade";
//import Image from "react-bootstrap/Image";
//import logo from "assets/sml-logo-vertical-white-all-trans.png";

export const TwoColumnHero: React.FC = ({ children }) => {
  return (
    <Container className="h-100">
      <Row className="h-100">
        <Col xs={12} xl={12}>
          <Fade className="h-100" appear in>
            <Container className="bg-light d-flex flex-column justify-content-center align-items-center px-5">
              {children}
            </Container>
          </Fade>
        </Col>
      </Row>
    </Container>
  );
};
