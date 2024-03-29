import React from "react";
import useLoginOnApiKey from "./useLoginOnApiKey";
import useLocalStoragedApiKey from "./useLocalStoragedApiKey";
import { UserContext } from "contexts";
import { Top, Routes, Footer, Loader } from "../";

export const Main: React.FC = () => {
  const {
    state: { loggedIn },
  } = React.useContext(UserContext);

  useLocalStoragedApiKey();
  useLoginOnApiKey();

  if (loggedIn === null) {
    return <Loader />;
  }

  return (
    <>
      <Top />
      <Routes />
      <Footer />
    </>
  );
};
