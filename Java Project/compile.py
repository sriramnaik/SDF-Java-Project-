import subprocess
import sys
import os

def compile_run(args):
    if not os.path.exists("build"):
        subprocess.run(["ant"])
    subprocess.run(["java","-cp","build","MyInfArith"] + args)