/^[[:space:]]*"body": / {
  # Convert character sequences to character constants
  gsub(/\\r/, "")
  gsub(/\\n/, "\n")
  # Remove trailing punctuation
  gsub(/\n*",$/, "")
  # Look for the build dependency tag
  i=split($0, a, /### Build Dependencies/)
  if ( i != 2 ) {
    print "no build dependencies"
    exit 0
  }
  # Throw away everything up to the build dependency tag
  $0=a[2]
  # Grab the specific build dependencies
  i=split($0,a,/\nBuild with: /)
  delete a[1]
  # Format the individual dependencies
  errLines = 0
  depLines = 0
  for (i in a) {
    # Remove trailing text
    gsub(/\n.*$/, "", a[i])
    n=split(a[i], d, /\s+/)
    switch (d[1]) {
      case /maven-version/ :
        if (n != 3) {
          err[errLines++]="unrecognized maven-version stanza: " a[i] "(" n ")"
        }
        break

      case /git-clone/ :
        if (n != 4) {
          err[errLines++]="unrecognized git-clone stanza: " a[i] "(" n ")"
        }
        break

      default:
        err[errLines++]="unrecognized build type: " d[1] " - " a[i]
        break

    }
    # If we don't have any errors, format this dependency line and add it
    #  to the list of dependency lines.
    if (errLines == 0) {
      sep=""
      line=""
      for (n in d) {
        line=line sep d[n]
        sep=" "
      }
      dep[depLines++]=line
    }
  }
  # Print either errors (to stderr) or the dependency lines (to stdout)
  if (errLines == 0) {
      for (n in dep) {
        print dep[n]
      }
  } else {
      for (n in err) {
        print err[n] > "/dev/stderr"
      }
      exit 1
  }
}
