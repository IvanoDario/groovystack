package wevver.domain

import groovy.transform.EqualsAndHashCode
import groovy.transform.Immutable
import groovy.transform.ToString

/**
 * Immutable City POGO
 */
@EqualsAndHashCode
@ToString
@Immutable
class City {
    String name
    String state
    Double latitude
    Double longitude
}