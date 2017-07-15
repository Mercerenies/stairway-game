#!/usr/bin/perl

use strict;
use warnings;
use 5.010;

open(my $fh, '<', "./stairway/enemy/$ARGV[0].scala") or die("$!");
while (<$fh>) {
    say if /def spoils/;
}
close $fh
