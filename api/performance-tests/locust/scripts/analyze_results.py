#!/usr/bin/env python3
"""
Performance Test Results Analyzer
Analyzes Locust test results and generates performance reports
"""

import csv
import json
import sys
import argparse
from datetime import datetime
from pathlib import Path


class PerformanceAnalyzer:
    """Analyzes Locust performance test results"""
    
    def __init__(self, results_dir="reports"):
        self.results_dir = Path(results_dir)
        self.thresholds = {
            "destination_endpoint": {
                "max_response_time_ms": 500,
                "percentile_95_ms": 300,
                "error_rate_percent": 1.0
            },
            "search_endpoint": {
                "max_response_time_ms": 2000,
                "percentile_95_ms": 1500,
                "error_rate_percent": 2.0
            }
        }
    
    def analyze_stats_file(self, stats_file):
        """Analyze the _stats.csv file from Locust"""
        results = {
            "endpoints": {},
            "summary": {},
            "pass_fail": {}
        }
        
        with open(stats_file, 'r') as f:
            reader = csv.DictReader(f)
            for row in reader:
                if row['Type'] == 'GET':
                    endpoint_name = row['Name']
                    
                    # Parse metrics
                    avg_response_time = float(row['Average Response Time'])
                    min_response_time = float(row['Min Response Time'])
                    max_response_time = float(row['Max Response Time'])
                    p95_response_time = float(row['95%'])
                    p99_response_time = float(row['99%'])
                    requests_per_sec = float(row['Requests/s'])
                    failure_count = int(row['Failure Count'])
                    request_count = int(row['Request Count'])
                    
                    # Calculate error rate
                    error_rate = (failure_count / request_count * 100) if request_count > 0 else 0
                    
                    results["endpoints"][endpoint_name] = {
                        "avg_response_time_ms": avg_response_time,
                        "min_response_time_ms": min_response_time,
                        "max_response_time_ms": max_response_time,
                        "p95_response_time_ms": p95_response_time,
                        "p99_response_time_ms": p99_response_time,
                        "requests_per_second": requests_per_sec,
                        "total_requests": request_count,
                        "failed_requests": failure_count,
                        "error_rate_percent": error_rate
                    }
        
        return results
    
    def check_thresholds(self, results):
        """Check if results meet performance thresholds"""
        pass_fail = {}
        
        for endpoint, metrics in results["endpoints"].items():
            endpoint_type = None
            if "destination" in endpoint.lower():
                endpoint_type = "destination_endpoint"
            elif "searchresults" in endpoint.lower():
                endpoint_type = "search_endpoint"
            
            if endpoint_type:
                thresholds = self.thresholds[endpoint_type]
                
                checks = {
                    "max_response_time": metrics["max_response_time_ms"] <= thresholds["max_response_time_ms"],
                    "p95_response_time": metrics["p95_response_time_ms"] <= thresholds["percentile_95_ms"],
                    "error_rate": metrics["error_rate_percent"] <= thresholds["error_rate_percent"]
                }
                
                pass_fail[endpoint] = {
                    "checks": checks,
                    "overall_pass": all(checks.values()),
                    "thresholds_used": thresholds
                }
        
        return pass_fail
    
    def generate_report(self, results, pass_fail):
        """Generate a formatted performance report"""
        report = []
        report.append("=" * 80)
        report.append("HOTEL SEARCH API PERFORMANCE TEST REPORT")
        report.append("=" * 80)
        report.append(f"Generated: {datetime.now().strftime('%Y-%m-%d %H:%M:%S')}")
        report.append("")
        
        # Overall summary
        total_requests = sum(ep["total_requests"] for ep in results["endpoints"].values())
        total_failures = sum(ep["failed_requests"] for ep in results["endpoints"].values())
        overall_error_rate = (total_failures / total_requests * 100) if total_requests > 0 else 0
        
        report.append("OVERALL SUMMARY")
        report.append("-" * 40)
        report.append(f"Total Requests: {total_requests:,}")
        report.append(f"Total Failures: {total_failures:,}")
        report.append(f"Overall Error Rate: {overall_error_rate:.2f}%")
        report.append("")
        
        # Endpoint details
        for endpoint, metrics in results["endpoints"].items():
            report.append(f"ENDPOINT: {endpoint}")
            report.append("-" * 60)
            report.append(f"  Total Requests: {metrics['total_requests']:,}")
            report.append(f"  Requests/sec: {metrics['requests_per_second']:.2f}")
            report.append(f"  Error Rate: {metrics['error_rate_percent']:.2f}%")
            report.append(f"  Response Times (ms):")
            report.append(f"    Average: {metrics['avg_response_time_ms']:.1f}")
            report.append(f"    Min: {metrics['min_response_time_ms']:.1f}")
            report.append(f"    Max: {metrics['max_response_time_ms']:.1f}")
            report.append(f"    95th percentile: {metrics['p95_response_time_ms']:.1f}")
            report.append(f"    99th percentile: {metrics['p99_response_time_ms']:.1f}")
            
            # Pass/Fail status
            if endpoint in pass_fail:
                pf = pass_fail[endpoint]
                status = "PASS" if pf["overall_pass"] else "FAIL"
                report.append(f"  Status: {status}")
                
                if not pf["overall_pass"]:
                    report.append("  Failed Checks:")
                    for check, passed in pf["checks"].items():
                        if not passed:
                            report.append(f"    - {check}")
            
            report.append("")
        
        # Recommendations
        report.append("RECOMMENDATIONS")
        report.append("-" * 40)
        
        recommendations = []
        
        for endpoint, pf in pass_fail.items():
            if not pf["overall_pass"]:
                if not pf["checks"]["max_response_time"]:
                    recommendations.append(f"• {endpoint}: Optimize for faster response times")
                if not pf["checks"]["error_rate"]:
                    recommendations.append(f"• {endpoint}: Investigate and fix errors causing failures")
        
        if not recommendations:
            recommendations.append("• All endpoints are performing within acceptable thresholds")
            recommendations.append("• Consider running stress tests with higher load")
        
        for rec in recommendations:
            report.append(rec)
        
        return "\n".join(report)
    
    def find_latest_results(self):
        """Find the most recent test results"""
        stats_files = list(self.results_dir.glob("*_stats.csv"))
        if not stats_files:
            return None
        
        # Sort by modification time, return most recent
        latest_file = max(stats_files, key=lambda f: f.stat().st_mtime)
        return latest_file
    
    def analyze_latest(self):
        """Analyze the most recent test results"""
        stats_file = self.find_latest_results()
        if not stats_file:
            print("No test results found in reports directory")
            return
        
        print(f"Analyzing results from: {stats_file}")
        results = self.analyze_stats_file(stats_file)
        pass_fail = self.check_thresholds(results)
        report = self.generate_report(results, pass_fail)
        
        # Save report
        report_file = stats_file.parent / f"analysis_{stats_file.stem.replace('_stats', '')}.txt"
        with open(report_file, 'w') as f:
            f.write(report)
        
        print(f"Analysis saved to: {report_file}")
        print("\n" + report)


def main():
    parser = argparse.ArgumentParser(description="Analyze Locust performance test results")
    parser.add_argument("--file", help="Specific stats file to analyze")
    parser.add_argument("--dir", default="reports", help="Results directory (default: reports)")
    
    args = parser.parse_args()
    
    analyzer = PerformanceAnalyzer(args.dir)
    
    if args.file:
        results = analyzer.analyze_stats_file(args.file)
        pass_fail = analyzer.check_thresholds(results)
        report = analyzer.generate_report(results, pass_fail)
        print(report)
    else:
        analyzer.analyze_latest()


if __name__ == "__main__":
    main()
