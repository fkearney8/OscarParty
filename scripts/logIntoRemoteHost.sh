#!/bin/sh
script_dir="$(dirname $0)"
exec ssh -l ubuntu -i "${script_dir}/../private/OscarPartyPrivateKey.pem" ec2-54-165-191-142.compute-1.amazonaws.com
