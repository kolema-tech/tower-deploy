#!/usr/bin/env bash
ansible-playbook eureka-out.yml -i eureka-out.inv --user=pz --private-key=/home/pz/.ssh/id_rsa -s -v