import React from "react";
import { Route, Redirect } from "react-router-dom";
import { connect } from "react-redux";

const SecuredRoute = ({ component: Component, user, ...otherProps }) => (
  <Route
    {...otherProps}
    render={props => (user.validToken === true ? <Component {...props} /> : <Redirect to="/login" />)}
  />
);

const mapStateToProps = state => ({
  user: state.user
});

export default connect(mapStateToProps)(SecuredRoute);
