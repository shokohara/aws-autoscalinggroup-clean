package com.github.skohar.awsautoscalingclean

import scopt.OptionParser

object Main {

  val parser = new OptionParser[Config]("scopt") {
    opt[Int]("delete-count") required() action { (x, c) =>
      c.copy(deleteCount = x)
    }
    opt[Boolean]("dry-run") optional() action { (x, c) =>
      c.copy(dryRun = x)
    }
  }

  def main(args: Array[String]): Unit = {
    parser.parse(args, Config()) match {
      case Some(config) => Worker.detachAutoScalingGroup(config.deleteCount, config.dryRun)
      case None =>
    }
  }
}
