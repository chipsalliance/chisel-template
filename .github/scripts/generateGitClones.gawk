# Generate commands to:
#  - clone, build, and publish dependencies, (on stdout)
#  - fetch corresponding shas ( on fd 3)
# Specify "-v verbose=1" on the command line for verbose output (to stderr)
/#/ {
  # Strip comments
  gsub(/#.*$/,"")
}
/git-clone/ {
  project=$2
  repo=$3
  tag=$4
  if (verbose) {
    print "writing clone and build commands to stdout." > "/dev/stderr"
  }
  # Generate commands to do a shallow fetch of all branches, checkout the desired ref/sha, and build and publish the jars.
  printf "git clone --no-single-branch --no-checkout --depth 5 %s %s && (cd %s && git checkout %s && sbt +publishLocal)\n",repo,project,project,tag
  # If the tag looks like a SHA, assume it is and generate a command to just echo it,
  #  otherwise, generate a string to fetch the sha from the remote.
  # In either case, write the output to fd 3.
  if (verbose) {
    print "writing sha generation commands to fd 3." > "/dev/stderr"
  }
  if ( tag ~ /^[[:xdigit:]]+$/ ) {
    printf "echo %s\n", tag > "/dev/fd/3"
  } else {
    printf "git ls-remote --tags --heads %s %s", repo, tag > "/dev/fd/3"
  }
}
