'use strict';

import DismissableAlert from './DismissableAlert';
import React from 'react';

export default class EntryAddedAlert extends React.Component {

    constructor(props) {
        super(props);
    }

    render() {
        if (this.props.insertResult) {
            if (this.props.insertResult.successful) {
                var description = <p> Just added an aircraft to the queue at: <strong> {this.props.insertResult.entry.requestTime} </strong></p>
                return (
                    <DismissableAlert alertVisible={this.props.insertResult != null} title="Success!" description={description} style="success"/>
                );
            } else {
                return (
                    <DismissableAlert alertVisible={this.props.insertResult != null} title="Oops!" description="An Error occurred while adding aircraft to the queue." style="warning"/>
                );
            }
        } else {
            return null;
        }

    }
}