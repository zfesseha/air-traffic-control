'use strict';

import DismissableAlert from './DismissableAlert';
import React from 'react';

export default class EntryAddedAlert extends React.Component {

    constructor(props) {
        super(props);
    }

    render() {
        if (this.props.dequeuedEntry) {
            if (this.props.dequeuedEntry.successful) {
                // TODO: Add an identifier to the plane.
                var description = <p> The aircraft added at: <strong> {this.props.dequeuedEntry.entry.requestTime} </strong> is up next and removed from the queue.</p>
                return (
                    <DismissableAlert alertVisible={this.props.dequeuedEntry != null} title="Success!" description={description} style="success"/>
                );
            } else {
                var description = <p>An Error occurred while attempting to retrieve the next aircraft in the queue. Error was <code>{this.props.dequeuedEntry.message}</code></p>
                return (
                    <DismissableAlert alertVisible={this.props.dequeuedEntry != null} title="Oops!" description={description} style="warning"/>
                );
            }
        } else {
            return null;
        }

    }
}