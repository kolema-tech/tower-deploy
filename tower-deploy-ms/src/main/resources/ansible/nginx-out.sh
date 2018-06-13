#!/usr/bin/env bash
ansible-playbook nginx-out.yml -i nginx-out.inv --user=pz --private-key=/home/pz/.ssh/id_rsa -s -v