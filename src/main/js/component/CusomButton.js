'use strict';

import {Button} from 'react-bootstrap';

import React from 'react';

export default class CustomButton extends React.Component {
    constructor(props) {
        super(props);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleSubmit(e) {
        e.preventDefault();
        this.props.onSubmit();
    }

    render() {
        return (
            <Button bsStyle={this.props.style} className="ac-button" onClick={this.handleSubmit}>{this.props.title}</Button>
        )
    }
}
