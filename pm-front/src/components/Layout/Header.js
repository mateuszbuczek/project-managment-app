import React, { Component } from "react";
import { Link } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { logout } from "../../actions/securityActions";

class Header extends Component {
  constructor(props) {
    super(props);

    this.state = {
      loggedIn: false
    };
  }
  componentWillReceiveProps = nextProps => {
    if (nextProps.user) {
      this.setState({
        loggedIn: Object.keys(nextProps.user.user).length !== 0
      });
    }
  };

  render() {
    return (
      <nav className="navbar navbar-expand-sm navbar-dark bg-primary mb-4">
        <div className="container">
          <Link className="navbar-brand" to="/">
            Project Management App
          </Link>
          <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#mobile-nav">
            <span className="navbar-toggler-icon" />
          </button>

          <div className="collapse navbar-collapse" id="mobile-nav">
            <ul className="navbar-nav mr-auto">
              <li className="nav-item">
                <Link className="nav-link" to="/dashboard">
                  Dashboard
                </Link>
              </li>
            </ul>

            <ul className="navbar-nav ml-auto">
              <li className="nav-item">
                {this.state.loggedIn ? (
                  <Link className="nav-link " to="/dashboard">
                    <i className="fas fa-user-circle mr-1" />
                    {this.props.user.user.fullName}
                  </Link>
                ) : (
                  <Link className="nav-link " to="/register">
                    Sign Up
                  </Link>
                )}
              </li>
              <li className="nav-item">
                {this.state.loggedIn ? (
                  <Link className="nav-link" to="/" onClick={this.props.logout}>
                    Logout
                  </Link>
                ) : (
                  <Link className="nav-link" to="/login">
                    Login
                  </Link>
                )}
              </li>
            </ul>
          </div>
        </div>
      </nav>
    );
  }
}

const mapStateToProps = state => ({
  user: state.user
});

export default connect(
  mapStateToProps,
  { logout }
)(Header);
