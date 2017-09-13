'use strict';

import QueueEntry from './QueueEntry';

import React from 'react';

export default class AircraftQueue extends React.Component{
    render() {
        var queue = this.props.queue.map(entry =>
            <QueueEntry key={entry.requestTime} entry={entry}/>
        );
        if (queue.length > 0) {

            return (
                <div>
                    <table className="table table-bordered table-striped table-hover">
                        <thead>
                        <tr>
                            <th>Type</th>
                            <th>Size</th>
                            <th>Added to Queue</th>
                        </tr>
                        </thead>
                        <tbody>
                            {queue}
                        </tbody>
                    </table>
                </div>
            )
        } else {
            return (
                <div>
                    There are no aircrafts in the queue at this time. Aircrafts will show up here when they get added to the system.
                </div>
            )
        }
    }
}
