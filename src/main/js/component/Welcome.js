'use strict';

import {Button, Jumbotron} from 'react-bootstrap';

import React from 'react';

export default class Welcome extends React.Component {
    constructor(props) {
        super(props);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleSubmit(e) {
        e.preventDefault();
        this.props.onStart();
    }

    render() {
        return (
            <Jumbotron>
                <h1>Air Traffic Control!</h1>
                <p>Welcome to the Air Traffic Control System. This is a simple system that prioritizes air traffic based on Aircraft type and size. Click 'Start Queue' to get started.</p>
                <Button className="ac-control" bsStyle="warning" onClick={this.handleSubmit}>Start Queue</Button>
            </Jumbotron>
        )
    }
}
